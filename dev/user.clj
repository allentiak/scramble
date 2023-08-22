(ns user
  (:require
   [ring.adapter.jetty :as jetty]
   [allentiak.scramble.rest-api :as rest-api]))

(defonce server
  (atom nil))

(defn start-web-server []
  (if (nil? @server)
    ((reset! server
       (jetty/run-jetty #'rest-api/webapp {:port 3000, :join? false}))
     (println "Development HTTP Server running on port 3000..."))
    (println "Cannot start server: Server already running!")))

(defn stop-web-server []
  (if-not (nil? @server)
    ((@server :timeout 100)
     (.stop @server)
     (reset! server nil)
     (println "Server stopped."))
    (println "Cannot stop server: Server not running!")))

(defn restart-web-server []
  (stop-web-server)
  (start-web-server))

(comment
  (start-web-server)
  @server
  (restart-web-server)
  (stop-web-server))
