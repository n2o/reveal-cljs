run: install repl

install:
	@echo ":: Install dependencies"
	yarn install

repl:
	@echo ":: Start an interactive REPL"
	clj -A:fig

clean:
	@echo ":: Clean"
	rm -rf target/
	rm -rf resources/public/node_modules

web: install
	mkdir -p resources/public/cljs-out
	@echo ":: Build Project"
	clj -A:fig/simple
	@echo ":: Copy generated assets to resources-folder"
	cp target/public/cljs-out/dev-main.js resources/public/cljs-out
	@echo ":: Now open 'resources/public/index.html' to find the presentation"

ancient:
	@echo ":: Check for old dependencies"
	clojure -Sdeps '{:deps {olical/depot {:mvn/version "1.8.4"}}}' -m depot.outdated.main