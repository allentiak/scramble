(ns allentiak.scramble.rest-api.routes
  (:require
   [allentiak.scramble.rest-api.examples :as examples]
   [allentiak.scramble.rest-api.handlers :as handlers]
   [allentiak.scramble.rest-api.specs :as specs]
   [reitit.openapi :as openapi]
   [reitit.swagger :as swagger]))

(def scramble
  ["/scramble"
   {:tags ["scramble"]
    :get
    {:summary "scramble with query parameters"
     :parameters specs/scramble-parameters--get
     :responses specs/scramble-response
     :handler handlers/scramble-get-handler
     :openapi
     {:requestQuery
      {:content
       {"application/json"
        examples/request}}
      :responses
      examples/response-200}}
    :post
    {:summary "scramble with body parameters"
     :parameters specs/scramble-parameters--post
     :responses specs/scramble-response
     :handler handlers/scramble-post-handler
     :openapi
     {:requestBody
      {:content
       {"application/json"
        examples/request}}
      :responses
      examples/response-200}}}])

(def api-documentation
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
