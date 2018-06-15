(ns clj-hello.datastore
  (:import [com.google.appengine.api.datastore DatastoreServiceFactory]
           [com.google.appengine.api.datastore Query]
           [com.google.appengine.api.datastore FetchOptions$Builder]
           [com.google.appengine.api.datastore Entity]))

(defn get-all
  [kind prop-name]
  (filter some?
          (for [ds-result
                (some->
                 (DatastoreServiceFactory/getDatastoreService)
                 (.prepare (Query. kind))
                 (.asList (FetchOptions$Builder/withDefaults)))]
            (clojure.edn/read-string (.getProperty ds-result prop-name)))))

(defn delete-all
  [kind prop-name]
  (.delete (DatastoreServiceFactory/getDatastoreService)
           (for [ds-result
                 (some->
                  (DatastoreServiceFactory/getDatastoreService)
                  (.prepare (Query. kind))
                  (.asList (FetchOptions$Builder/withDefaults)))]
             (.getKey ds-result))))

(defn get-one
  [kind prop-name]
  (first (get-all kind prop-name)))

(defn put-append
  [kind prop-name value]
  (let [entity (Entity. kind)
        as-string (pr-str value)]
    ;; verify read-ability
    (clojure.edn/read-string as-string)
    (.setProperty entity prop-name as-string)
    (some-> (DatastoreServiceFactory/getDatastoreService)
            (.put entity)
            str)))

(defn put-replace
  [kind prop-name value]
  (delete-all kind prop-name)
  (put-append kind prop-name value))
