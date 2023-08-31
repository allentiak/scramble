(ns user
  (:require
   [ring.adapter.jetty :as jetty]
   [allentiak.scramble.rest-api.core :as rest-api]))

(defonce dev-server
  (atom nil))

(defn start-dev-server []
  (if-not @dev-server
    (do
      (reset! dev-server
              ;; FIXME: broken! I get a 500 error:
              ;;
              ;; HTTP ERROR 500 java.lang.IllegalArgumentException:
              ;; No implementation of method: :match-by-path of protocol:
              ;; #'reitit.core/Router found for class:
              ;; allentiak.scramble.rest_api.core$dev_router
              (jetty/run-jetty #'rest-api/prod-webapp {:port 3000 :join? false}))
      (println "Development server running on port 3000..."))
    (println "Cannot start Development server: Server already running!")))

(defn stop-dev-server []
  (if @dev-server
    (do
      (.stop @dev-server)
      (reset! dev-server nil)
      (println "Development server stopped."))
    (println "Cannot stop Development server: Server not running!")))

(defn restart-dev-server []
  (stop-dev-server)
  (start-dev-server))

(defonce prod-server
  (atom nil))

(defn start-prod-server []
  (if-not @prod-server
    (do
      (reset! prod-server
              (jetty/run-jetty rest-api/prod-webapp {:port 3333 :join? false}))
      (println "Production server running on port 3333..."))
    (println "Cannot start Produciton server: Server already running!")))

(defn stop-prod-server []
  (if @prod-server
    (do
      (.stop @prod-server)
      (reset! prod-server nil)
      (println "Production server stopped."))
    (println "Cannot stop Production server: Server not running!")))

(defn restart-prod-server []
  (do
    (stop-prod-server)
    (start-prod-server)))

(comment
  (start-dev-server)
  @dev-server
  (restart-dev-server)
  (stop-dev-server)
  ,
  @prod-server
  (start-prod-server)
  (restart-prod-server)
  (stop-prod-server)
  ,)
