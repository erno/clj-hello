(ns clj-hello.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.tools.nrepl.server :as nrepl-server])
  (:import (com.google.appengine.api.users User UserService UserServiceFactory)))

(defn user-email
  []
  (println (UserServiceFactory/getUserService))
  (some-> (UserServiceFactory/getUserService)
          (.getCurrentUser)
          (.getEmail)))

(defn init-app
  []
  (println "hello from init function! env:" (System/getenv "GAE_ENV")))

(defroutes app
  (GET "/" [] (str "<h1>Hello, "  (or (user-email) "stranger") "</h1>"))
  (route/not-found "<h1>Page not found</h1>"))
