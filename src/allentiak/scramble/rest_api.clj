(ns allentiak.scramble.rest-api
  (:require
    [allentiak.scramble.backend :as backend]
    [malli.util :as mu]
    [muuntaja.core :as m]
    [reitit.coercion.malli :as malli-coercion]
    [reitit.dev.pretty :as pretty]
    [reitit.openapi :as openapi]
    [reitit.swagger :as swagger]
    [reitit.swagger-ui :as swagger-ui]
    [reitit.ring :as reitit-ring]
    [reitit.ring.coercion :as coercion]
    [reitit.ring.middleware.exception :as exception]
    [reitit.ring.middleware.multipart :as multipart]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.parameters :as parameters]
    [ring.adapter.jetty :as jetty]))


(defn scramble-get-handler
  [{{{:keys [letters word]} :query} :parameters}]
  {:status 200
   :body (backend/scramble? letters word)})


(defn scramble-post-handler
  [{{{:keys [letters word]} :body} :parameters}]
  {:status 200
   :body (backend/scramble? letters word)})


(defn- parameters-malli-spec-map []
  {:query
   [:map
    [:letters
     {:title "letters parameter"
      :description "description for letters parameter"
      :json-schema/default "abc"}
     string?]
    [:word
     {:title "word parameter"
      :description "description for word parameter"
      :json-schema/default "abc"}
     string?]]})

(defn- examples-request-json-content-map []
  {:examples
   {"scramble?-abc-a"
    {:summary "abc-a"
     :value {:letters "abc"
             :word "a"}}
    "scramble?-a-abc"
    {:summary "a-abc"
     :value {:letters "a"
             :word "abc"}}}})

(defn- examples-response-200-json-content-map []
  {:examples
   {"true"
    {:summary "true"
     :value {:scramble? true}}
    "false"
    {:summary "false"
     :value {:scramble? false}}}})

(defn routes []
  [["/openapi.json"
    {:get {:no-doc  true
           :openapi {:info {:title "my-api"
                            :description "openapi3 docs with [malli](https://github.com/metosin/malli) and reitit-ring"
                            :version "0.0.1"}}
           :handler (openapi/create-openapi-handler)}}]
   ["/swagger.json"
    {:get {:no-doc  true
           :swagger {:info {:title "my-api"
                            :description "swagger docs with [malli](https://github.com/metosin/malli) and reitit-ring"
                            :version "0.0.1"}}
           :handler (swagger/create-swagger-handler)}}]
   ["/scramble"
    {:tags ["scramble"]
     :get
     {:summary "scramble with query parameters"
      :parameters (parameters-malli-spec-map)
      :responses {200
                  {:query [:map [:scramble? boolean?]]}}
      :handler scramble-get-handler
      :openapi
      {:requestQuery
       {:content
        {"application/json"
         (examples-request-json-content-map)}}
       :responses
       {200
        {:content
         {"application/json"
          (examples-response-200-json-content-map)}}}}}
     :post
     {:summary "scramble with body parameters"
      :parameters (parameters-malli-spec-map)
      :responses {200
                  {:body [:map [:scramble? boolean?]]}}
      :handler scramble-post-handler
      :openapi
      {:requestBody
       {:content
        {"application/json"
         (examples-request-json-content-map)}}
       :responses
       {200
        {:content
         {"application/json"
          (examples-response-200-json-content-map)}}}}}}]])


(def webapp
  (reitit-ring/ring-handler
    (reitit-ring/router
      (routes)
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
                           ;; coercing response bodys
                           coercion/coerce-response-middleware
                           ;; coercing request parameters
                           coercion/coerce-request-middleware
                           ;; multipart
                           multipart/multipart-middleware]}})

    (reitit-ring/routes
      (swagger-ui/create-swagger-ui-handler
        {:path "/"
         :config {:validatorUrl nil
                  :urls [{:name "swagger", :url "swagger.json"}
                         {:name "openapi", :url "openapi.json"}]
                  :urls.primaryName "openapi"
                  :operationsSorter "alpha"}})
      (reitit-ring/create-default-handler))))

(defn start []
  (jetty/run-jetty #'webapp {:port 3000, :join? false})
  (println "HTTP Server running on port 3000..."))

(comment
  (start))
