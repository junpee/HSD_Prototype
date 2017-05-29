'use strict';
var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var pump = require('pump');

gulp.task('minify', function (cb) {
  pump([
        gulp.src(['WebContent/awag/app.js','WebContent/awag/service.js','WebContent/awag/namevalue.js','WebContent/awag/**/*.js']),
        concat('main.js'),
        uglify(),
        gulp.dest('WebContent')
    ],
    cb
  );
});

gulp.task('default', ['minify']);
