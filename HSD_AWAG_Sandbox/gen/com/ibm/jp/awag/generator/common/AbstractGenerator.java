package com.ibm.jp.awag.generator.common;

import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.getStackErrorMessage;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.printError;
import static com.ibm.jp.awag.generator.common.util.GeneratorUtil.print;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.ibm.jp.awag.generator.common.model.AWAGConfig;
import com.ibm.jp.awag.generator.common.model.GeneratorConfig.TemplateConfig;
import com.ibm.jp.awag.generator.common.model.GeneratorConfig.TemplateSet;
import com.ibm.jp.awag2.generator.model.common.GeneratorDataMapKey;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * Generatorの抽象クラス。
 * 継承して各Generator独自のロジックを実装。
 *
 */
public abstract class AbstractGenerator {
	
	/** FreeMarkerのConfigurationオブジェクト */
	protected Configuration freeMarkerConfig;
	
	/** Generator設定を格納したオブジェクト */
	protected AWAGConfig awagConfig;
	
	/** Generatorの起動時刻 */
	private LocalDateTime requestTime = LocalDateTime.now();

	/** 上書き禁止ファイル用の生成コードの拡張子 */
	protected static final String GEN_FILE_SUFFIX_FOR_READONLY = ".gen";
		
	/** Template埋込データのローダー */
	protected AbstractInputDataLoader loader;

	/** コード生成成功件数 */
	protected int success;
	
	/** コード生成失敗件数 */
	protected int fail;
	
	/**
	 * 指定した埋込データとテンプレートセットでコードを生成する。
	 * @param dataModel 埋込データ
	 * @param templateSet テンプレートセット
	 */
	protected abstract void generateCodeFromTemplateSet(Map<String, Object> dataModel, TemplateSet templateSet);
	
	/**
	 * 唯一のコンストラクタ
	 * 
	 * @param configName
	 *            設定ファイルの名称
	 */
	public AbstractGenerator(AWAGConfig awagConfig) {
		
		this.awagConfig = awagConfig;
		freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_24);
		freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		freeMarkerConfig.setLogTemplateExceptions(false);
		
		// コード生成入力情報のデータローダーを設定する
		String dataLoaderClass = awagConfig.getGeneratorConfig().getDataLoaderClassName();
		if (dataLoaderClass != null && !dataLoaderClass.isEmpty()) {
			
			try {
				this.setLoader((AbstractInputDataLoader) Class.forName(dataLoaderClass).getConstructor(AWAGConfig.class).newInstance(awagConfig));
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				print("デフォルトのDataLoaderを使用" + getStackErrorMessage(e));
			}
		}
	}

	/**
	 * データローダーを取得する。
	 * @return loader
	 */
	public AbstractInputDataLoader getLoader() {
		return loader;
	}

	/**
	 * データローダーを設定する。
	 * @param loader セットする loader
	 */
	public void setLoader(AbstractInputDataLoader loader) {
		this.loader = loader;
	}
	
	/**
	 * Generatorのロジックを実行する。
	 * 
	 * @param xlsFilePath
	 *            リソースを定義したEXCELファイルのパス
	 */
	public void run() {

		print("Data Loader: " + this.loader.getClass().getName());
		
		// コード生成入力情報を読み込む
		Map<String, Object> dataModel = null;
		try {
			dataModel = this.loader.load();
		}  catch (GenerateException e) {
			throw new GenerateException("コード生成入力情報の読み込みに失敗。", e);
		}
		
		// バージョン番号の出力
		print("Input Data Generater Version : " + this.loader.getInputDataGeneratorVersion());
		
		// コード生成ロジックを実行
		try {
			
			// コード生成入力情報の設定（Template 共通分）
			dataModel.put("AWAGversion", this.awagConfig.getVersion());
			dataModel.put("GeneratorConfig", this.awagConfig.getGeneratorConfig());

			List<TemplateSet> templateSets = this.awagConfig.getGeneratorConfig().getTemplateSets();
			
			// テンプレートセット単位に子クラスのコード生成ロジックを呼び出す
			for (TemplateSet templateSet : templateSets) {

				print("=================================");
				print("TemplateSet:\t" + templateSet.getName());
				
				freeMarkerConfig.setDefaultEncoding(templateSet.getEncoding());
				freeMarkerConfig.setClassForTemplateLoading(Class.class, templateSet.getTemplatePackage());

				try {
					generateCodeFromTemplateSet(dataModel, templateSet);
				} catch (RuntimeException e) {
					e.printStackTrace();
					printError(getStackErrorMessage(e));
					continue;
				}
			}
			
			print("Process time: " + Duration.between(requestTime, LocalDateTime.now()).toString());
		} catch (GenerateException e) {
			throw new GenerateException("コード生成ロジックの実行に失敗。", e);
		}
	}

	/**
	 * リソース定義、テンプレートに埋め込むデータ、テンプレートを指定してコードを生成する。
	 * 
	 * @param resourceDef
	 *            リソース定義
	 * @param dataModel
	 *            テンプレートに埋め込むデータを含むMap
	 * @param targetTemplate
	 *            テンプレートセットの定義
	 * @param templateSet
	 *            使用するコード生成テンプレート
	 */
	protected void generateEachCode(Map<String, Object> dataModel, TemplateConfig targetTemplate, String codeDirectoryPath, String filePath) {
		
		// コード出力先ディレクトリの作成
		Path CodePath = Paths.get(codeDirectoryPath);

		try {
			Files.createDirectories(CodePath);
		} catch (IOException e) {
			throw new GenerateException("ディレクトリの作成に失敗。[Path]" + CodePath, e);
		}

		// テンプレートファイルの取得
		Template template;
		try {
			template = freeMarkerConfig.getTemplate(targetTemplate.getName() + ".ftl");
		} catch (IOException e) {
			throw new GenerateException("テンプレート取得に失敗。[Template]" + targetTemplate.getName() + ".ftl", e);
		}

		boolean successed = false;

		// コード生成
		try (BufferedWriter bw = Files.newBufferedWriter(new File(filePath).toPath(), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
			template.process(dataModel, bw);
			successed = true;
		} catch (IOException e) {
			if (targetTemplate.isAllowOverWrite()) {
				// 上書許可テンプレートではバックアップを取得しない
				
//				try {
//					Files.copy(new File(filePath).toPath(), new File(filePath + ".bak").toPath(), StandardCopyOption.REPLACE_EXISTING);
//				} catch (IOException e1) {
//					e1.printStackTrace();
//					filePath = filePath + GEN_FILE_SUFFIX_FOR_READONLY;
//				}
			} else {
				// 上書不可テンプレートでは接尾辞を追加して出力する
				filePath = filePath + GEN_FILE_SUFFIX_FOR_READONLY;		
			}
			try (BufferedWriter bw = Files.newBufferedWriter(new File(filePath).toPath())) {
				template.process(dataModel, bw);
				successed = true;
			} catch (IOException e1) {
				throw new GenerateException("テンプレートのOpenに失敗。[Template]" + targetTemplate.getName() + ".ftl", e1);
			} catch (TemplateException e2) {
				throw new GenerateException("テンプレートへのデータ埋め込みに失敗。[Template]" + targetTemplate.getName() + ".ftl", e2);
			}
			
		} catch (TemplateException e) {
			throw new GenerateException("テンプレートへのデータ埋め込みに失敗。[Template]" + targetTemplate.getName() + ".ftl", e);
		} finally {
			if (successed) {
				print("SUCCESS : \t[Template]" + targetTemplate.getName() + "\t[File]" + filePath);
			} else {
				print("FAIL : \t[Template]" + targetTemplate.getName() + "\t[File]" + filePath);
			}
		}
	}
}
