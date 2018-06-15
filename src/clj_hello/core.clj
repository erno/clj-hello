(ns clj-hello.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.string :as s]
            [clj-hello.datastore :as datastore]
            [clojure.tools.nrepl.server :as nrepl-server])
  (:import
   (com.google.appengine.api.users User UserService UserServiceFactory)
   (java.util.logging Logger)))

(def gae-log (Logger/getLogger "clj-hello"))

(defn log-info
  [& args]
  (.info gae-log (apply pr-str args)))

(defn user-email
  []
  (log-info (UserServiceFactory/getUserService))
  (some-> (UserServiceFactory/getUserService)
          (.getCurrentUser)
          (.getEmail)))

(defn login-link
  []
  (str "<a href='"
   (some-> (UserServiceFactory/getUserService)
           (.createLoginURL "/"))
   "'>Login</a>"))

(defn init-app
  []
  (log-info "hello from init function! env:" (System/getenv "GAE_ENV")))

(defroutes app
  (GET "/" [] (str "<h1>Hello, "  (or (user-email) "stranger") "</h1>"
                   (login-link)

                   "<p> Eval result:" (eval '(+ 1 1))))

  (GET "/write" []
       (datastore/put "fop" (str "x" (s/join "" (datastore/get "fop")))))
  (GET "/read" []
       (s/join "" (datastore/get "fop")))
  (route/not-found "<h1>Page not found</h1>"))
