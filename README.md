# reveal-cljs

![Clojure CI](https://github.com/n2o/reveal-cljs/workflows/Clojure%20CI/badge.svg)
[![https://github.com/n2o/reveal-cljs/releases](https://img.shields.io/github/release/n2o/reveal-cljs.svg)](https://github.com/n2o/reveal-cljs/releases)
[![https://github.com/n2o/reveal-cljs/blob/master/LICENSE](https://img.shields.io/github/license/n2o/reveal-cljs.svg)](https://github.com/n2o/reveal-cljs/blob/master/LICENSE)

Inspired by the great work on [reveal.js](https://github.com/hakimel/reveal.js/)
and the dislike towards JavaScript, this small project was created to provide a
simple wrapper in ClojureScript to create awesome presentations. All features
from reveal.js can be used. Check the
[official documentation of reveal.js](https://revealjs.com/) for more
information about it.

🌅 Find a demo of reveal-cljs on this page: https://n2o.github.io/reveal-cljs/

It uses Hiccup-syntax for templating, which makes it very easy to create slides.

Start the project with `make repl` and see your live
slides. Start *speaker-mode* by pressing <kbd>s</kbd> in the slides in your
browser.

[![sample.png](img/sample.png)](https://n2o.github.io/reveal-cljs/)

## Requirements

Tested with AdoptOpenJDK 16, see CI Build Job for more details in the
environment.

A JDK, [Clojure's CLI Tools](https://clojure.org/guides/getting_started), 
[yarn](https://yarnpkg.com/en/) and `make` are necessary to
build and run the project.

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

Everything in one command:

    make
    
Or:

Download the dependency reveal.js (specified in `package.json`) with:

    make install
    
or clone the repository:

    git clone git@github.com:hakimel/reveal.js.git resources/public/node_modules/reveal.js

To get an interactive development environment, run:

    make repl

Figwheel opens your browser at [localhost:9500](http://localhost:9500/). This
will auto compile and send all changes to the browser without the need to
reload.

Open Speaker Notes by pressing <kbd>s</kbd>.

## Compile and Prepare for Deployment

You can compile the cljs-Code and put the assets together to make your
presentation ready to be deployed on a webserver (e.g. on GitHub Pages).
By following these steps, you can publish the presentation on a regular
server, which only needs to serve HTML, CSS and JavaScript Files. This
is all put into one task:

    make web
    
Follow the steps on the terminal to find your prepared presentation.  

## Software

This project uses external packages, which can be removed by you, since most
of them are not essential:

* [reveal.js](https://github.com/hakimel/reveal.js) ([MIT License](https://github.com/hakimel/reveal.js/blob/master/LICENSE))
* [Bootstrap Grid](https://github.com/twbs/bootstrap) ([MIT License](https://github.com/twbs/bootstrap/blob/main/LICENSE))
* [Font-Awesome](https://github.com/FortAwesome/Font-Awesome/tree/master/js-packages/%40fortawesome/fontawesome-free) ([License](https://github.com/FortAwesome/Font-Awesome/blob/master/js-packages/%40fortawesome/fontawesome-free/LICENSE.txt))
* [Clojure](https://clojure.org/) ([EPL-1.0 License](https://opensource.org/licenses/eclipse-1.0.php))

## License

Copyright © 2016 – 2021 Christian Meter and Contributors

Distributed under the [MIT](LICENSE) License.
