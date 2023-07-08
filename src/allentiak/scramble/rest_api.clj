(ns allentiak.scramble.rest-api
  (:require
    [allentiak.scramble.backend :as backend]
    [hiccup.page :as h]
    [reitit.ring :as reitit-ring]
    [ring.adapter.jetty :as jetty]))


(defn landing-page []
  (h/html5
    [:head
     [:title "Landing page"]]
    [:body "Server running..."]))


(defn landing-page-handler
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (landing-page)})


(defn routes []
  [["/" {:summary "Show a landing page"
         :get landing-page-handler}]])


(def webapp
  (reitit-ring/ring-handler
    (reitit-ring/router
      (routes))
    (reitit-ring/create-default-handler)))

(defn start []
  (jetty/run-jetty #'webapp {:port 3000, :join? false})
  (println "HTTP Server running on port 3000..."))

(comment
  (start))
