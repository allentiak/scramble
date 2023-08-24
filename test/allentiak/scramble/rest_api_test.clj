(ns allentiak.scramble.rest-api-test
  (:require
   [allentiak.scramble.rest-api.core :refer [webapp]]
   [clojure.test :refer [deftest testing]]
   [expectations.clojure.test :refer [expect]]
   [ring.mock.request :as mock]))

(deftest webapp-smoke-test
  (testing "redirects to 'index.html' by default"
    (let [req (mock/request :get "/")
          response (webapp req)]
      (expect (= 302 (:status response)))
      (expect (= {"Location" "/index.html"} (:headers response)))))
  (testing "default page exists"
    (let [req (mock/request :get "/index.html")
          response (webapp req)]
      (expect (= 200 (:status response)))))
  (testing "invalid routes are detected"
    (let [req (mock/request :get "/whatever")
          response (webapp req)]
      (expect (= 404 (:status response))))))

(deftest well-formed-request-test
  (testing "well-formed request (with valid params)"
    (let [get-request (mock/request :get "/scramble")
          post-request (mock/request :post "/scramble")
          get-response (webapp (->
                                get-request
                                (mock/query-string {:letters "abc" :word "abc"})))
          post-response (webapp (->
                                 post-request
                                 (mock/json-body {:letters "abc" :word "abc"})))]
      (expect (= 200 (:status get-response)))
      (expect (= 200 (:status post-response)))))

  (testing "well-formed request (parameterless, yet valid)"
    (let [get-request (mock/request :get "/scramble")
          post-request (mock/request :post "/scramble")
          get-response (webapp (->
                                get-request
                                (mock/query-string {:letters "" :word ""})))
          post-response (webapp (->
                                 post-request
                                 (mock/json-body {:letters "" :word ""})))]
      (expect (= 200 (:status get-response))
              (= 200 (:status post-response))))))

(deftest malformed-or-semantically-incorrect-request
  (testing "malformed request (without params)"
    (let [get-request (mock/request :get "/scramble")
          post-request (mock/request :post "/scramble")
          get-response (webapp (->
                                get-request
                                (mock/query-string {})))
          post-response (webapp (->
                                 post-request
                                 (mock/json-body {})))]
      (expect (= 400 (:status get-response))
              (= 400 (:status post-response)))))

  (testing "malformed request (with only one param)"
    (let [only-letters-param {:letters ""}
          only-word-param {:word ""}
          get-request (mock/request :get "/scramble")
          post-request (mock/request :post "/scramble")
          get-response1 (webapp (->
                                 get-request
                                 (mock/query-string only-letters-param)))
          post-response1 (webapp (->
                                  post-request
                                  (mock/json-body only-letters-param)))
          get-response2 (webapp (->
                                 get-request
                                 (mock/query-string only-word-param)))
          post-response2 (webapp (->
                                  post-request
                                  (mock/json-body only-word-param)))]
      (expect (= 400 (:status get-response1)
                 (:status post-response1)))
      (expect (= 400 (:status get-response2)
                 (:status post-response2)))))

  (testing "semantically incorrect request (yet well-formed)"
    (let [incorrect-letters {:letters "22" :word "abc"}
          incorrect-word {:letters "abc" :word "222"}
          get-request (mock/request :get "/scramble")
          post-request (mock/request :post "/scramble")
          get-response1 (webapp (->
                                 get-request
                                 (mock/query-string incorrect-letters)))
          post-response1 (webapp (->
                                  post-request
                                  (mock/json-body incorrect-letters)))
          get-response2 (webapp (->
                                 get-request
                                 (mock/query-string incorrect-word)))
          post-response2 (webapp (->
                                  post-request
                                  (mock/json-body incorrect-word)))]
      (expect (= 400 (:status get-response1)
                 (:status get-response2)))
      (expect (= 400 (:status post-response1)
                 (:status post-response2))))))
