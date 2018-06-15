(ns clj-hello.datastore
  (:import [com.google.appengine.api.datastore DatastoreServiceFactory]
           [com.google.appengine.api.datastore Query]
           [com.google.appengine.api.datastore FetchOptions$Builder]
           [com.google.appengine.api.datastore Entity]))

(defn get
  [prop-name]
  (for [ds-result
        (some->
         (DatastoreServiceFactory/getDatastoreService)
         (.prepare (Query. "thing"))
         (.asList (FetchOptions$Builder/withDefaults)))]
    (.getProperty ds-result prop-name)))

(defn put
  [prop-name value]
  (let [entity (Entity. "thing")]
    (.setProperty entity prop-name value)
    (some-> (DatastoreServiceFactory/getDatastoreService)
            (.put entity)
            str)))
