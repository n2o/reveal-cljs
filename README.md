# reveal-cljs

Inspired by the great work on  [reveal.js](https://github.com/hakimel/reveal.js/) 
and the dislike towards JavaScript, this small project was created to
provide a simple wrapper in ClojureScript to create awesome presentations.

It uses Hiccup-syntax for templating, which makes it very easy to create
slides.

Currently, there is no speaker-mode etc. available, since this project uses
[lein-figwheel](https://github.com/bhauman/lein-figwheel) to get
hotcode-support in ClojureScript.

## Usage

Set your desired options in [core.cljs](https://github.com/n2o/reveal-cljs/blob/master/src/reveal/core.cljs#L8).
More options can be found [here](https://github.com/hakimel/reveal.js#configuration).

Create your slides in [slides.cljs](https://github.com/n2o/reveal-cljs/blob/master/src/reveal/slides.cljs)
and add them to the list in the function `all`.

Then start the development server as seen in the Setup section.

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. 

To clean all compiled files:

    lein clean

## License

Copyright © 2016 Christian Meter

Distributed under the Eclipse Public License either version 1.0.
