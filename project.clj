(defproject clj-hello "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [ring "1.7.0-RC1"]
                 [compojure "1.6.1"]
                 [org.clojure/tools.nrepl "0.2.12"]
                 [com.google.appengine/appengine-api-1.0-sdk "1.9.64"]]
  :ring {:handler clj-hello.core/app
         :init clj-hello.core/init-app}
  :plugins [[lein-ring "0.12.4"]
            #_[cider/cider-nrepl "0.17.0"]]
  :profiles {:dev
             {:source-paths ["dev/"]
              :dependencies
              [[com.google.appengine/appengine-api-stubs "1.9.64"]
               [com.google.appengine/appengine-local-runtime "1.9.64"]
               [com.google.appengine/appengine-local-runtime-shared "1.9.64"]]}}
  :target-path "target/%s"
  )
