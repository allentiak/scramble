(ns allentiak.scramble.rest-api-test
  (:require
    [allentiak.scramble.rest-api :refer [webapp]]
    [clojure.test :refer [deftest testing]]
    [expectations.clojure.test :refer [expect]]
    [ring.mock.request :as mock]
    [ring.util.codec :refer [form-encode]]))


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

(deftest get-endpoint-test
  (testing "well-formed GET request, with valid params"
    (let [response (webapp (->
                            (mock/request :get "/scramble")
                            (mock/query-string {:letters "abc" :word "abc"})))]
      (expect (= 200 (:status response)))))
  (testing "well-formed GET request, parameterless (yet valid)"
    (let [response (webapp (->
                            (mock/request :get "/scramble")
                            (mock/query-string {:letters "" :word ""})))]
      (expect (= 200 (:status response)))))
  (testing "malformed GET request, without params"
    (let [response (webapp (->
                            (mock/request :get "/scramble")
                            (mock/query-string {})))]
      (expect (= 400 (:status response))))))

(deftest post-endpoint-test
  (testing "well-formed POST request, with valid params"
    (let [response (webapp (->
                            (mock/request :post "/scramble")
                            (mock/json-body {:letters "abc" :word "abc"})))]
      (expect (= 200 (:status response)))))
  (testing "well-formed POST request, parameterless (yet valid)"
    (let [response (webapp (->
                            (mock/request :post "/scramble")
                            (mock/json-body {:letters "" :word ""})))]
      (expect (= 200 (:status response)))))
  (testing "malformed POST request, without params"
    (let [response (webapp (->
                            (mock/request :post "/scramble")
                            (mock/json-body {})))]
      (expect (= 400 (:status response))))))
