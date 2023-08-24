(ns allentiak.scramble.rest-api.core
  (:gen-class)
  (:require
   [allentiak.scramble.rest-api.routes :as routes]
   [malli.util :as mu]
   [muuntaja.core :as m]
   [reitit.coercion.malli :as malli-coercion]
   [reitit.dev.pretty :as pretty]
   [reitit.openapi :as openapi]
   [reitit.ring :as reitit-ring]
   [reitit.ring.coercion :as coercion]
   [reitit.ring.middleware.exception :as exception]
   [reitit.ring.middleware.multipart :as multipart]
   [reitit.ring.middleware.muuntaja :as muuntaja]
   [reitit.ring.middleware.parameters :as parameters]
   [reitit.swagger :as swagger]
   [reitit.swagger-ui :as swagger-ui]
   [ring.adapter.jetty :as jetty]))

(def ^:private router-config-map
  {:exception pretty/exception
   :data {:coercion (malli-coercion/create
                     {;; set of keys to include in error messages
                      :error-keys #{#_:type :coercion :in :schema :value :errors :humanized #_:transformed}
                          ;; schema identity function (default: close all map schemas)
                      :compile mu/closed-schema
                          ;; strip-extra-keys (effects only predefined transformers)
                      :strip-extra-keys true
                          ;; add/set default values
                      :default-values true
                          ;; malli options
                      :options nil})
          :muuntaja m/instance
          :middleware [;; swagger & openapi
                       swagger/swagger-feature
                       openapi/openapi-feature
                         ;; query-params & form-params
                       parameters/parameters-middleware
                         ;; content-negotiation
                       muuntaja/format-negotiate-middleware
                         ;; encoding response body
                       muuntaja/format-response-middleware
                         ;; exception handling
                       exception/exception-middleware
                         ;; decoding request body
                       muuntaja/format-request-middleware
                         ;; coercing response body
                       coercion/coerce-response-middleware
                         ;; coercing request parameters
                       coercion/coerce-request-middleware
                         ;; multipart
                       multipart/multipart-middleware]}})

(def api-documentation-routes
  [["/openapi.json"]
   {:get {:no-doc  true
          :openapi {:info {:title "my-api"
                           :description "openapi3 docs with [malli](https://github.com/metosin/malli) and reitit-ring"
                           :version "0.0.1"}}
          :handler (openapi/create-openapi-handler)}}
   ["/swagger.json"
    {:get {:no-doc  true
           :swagger {:info {:title "my-api"
                            :description "swagger docs with [malli](https://github.com/metosin/malli) and reitit-ring"
                            :version "0.0.1"}}
           :handler (swagger/create-swagger-handler)}}]])

(def api-routes
  [api-documentation-routes
   routes/scramble-api-route])

(def webapp
  (reitit-ring/ring-handler
   (reitit-ring/router
    api-routes
    router-config-map)
   (reitit-ring/routes
    (swagger-ui/create-swagger-ui-handler
     {:path "/"
      :config {:validatorUrl nil
               :urls [{:name "swagger", :url "swagger.json"}
                      {:name "openapi", :url "openapi.json"}]
               :urls.primaryName "openapi"
               :operationsSorter "alpha"}})
    (reitit-ring/create-default-handler))))

(defn -main []
  (jetty/run-jetty webapp {:port 3000, :join? false})
  (println "Production HTTP Server running on port 3000..."))