# reveal-cljs

Inspired by the great work on [reveal.js](https://github.com/hakimel/reveal.js/)
and the dislike towards JavaScript, this small project was created to provide a
simple wrapper in ClojureScript to create awesome presentations.

It uses Hiccup-syntax for templating, which makes it very easy to create
slides.

Start the project with `lein figwheel` and see your live slides. Start
*speaker-mode* by pressing <kbd>s</kbd> in the slides in your browser (thanks to
@kazesberger)

## Requirements

All common JDKs should work. I used a workaround [from this
issue](https://github.com/bhauman/lein-figwheel/issues/612) to make it
compatible to JDK9 until Clojure and ClojureScript are fully JDK9-compatible.

## Usage

Set your desired options in
[core.cljs](https://github.com/n2o/reveal-cljs/blob/master/src/reveal/core.cljs#L10).
More options can be found
[here](https://github.com/hakimel/reveal.js#configuration).

Create your slides in
[slides.cljs](https://github.com/n2o/reveal-cljs/blob/master/src/reveal/slides.cljs)
and add them to the list in the function `all`.

Then start the development server as seen in the Setup section.

## Installation & Setup

Download the dependency reveal.js (specified in `bower.json`) with:

    bower install
    
or clone the repository:

    git clone git@github.com:hakimel/reveal.js.git resources/public/bower_components/reveal.js

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. 

To clean all compiled files:

    lein clean

## License

Copyright © 2016-2018 Christian Meter and Contributors

Distributed under the [MIT](LICENSE) License version.
