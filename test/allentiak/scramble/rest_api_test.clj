(ns allentiak.scramble.rest-api-test
  (:require
    [allentiak.scramble.rest-api :refer [webapp landing-page]]
    [clojure.test :refer [deftest testing]]
    [expectations.clojure.test :refer [expect]]
    [ring.mock.request :as mock]
    [ring.util.codec :refer [form-encode]]))


(deftest webapp-smoke-test
  (testing "always valid route"
    (let [req (mock/request :get "/")
          response (webapp req)]
      (expect (= 200 (:status response)))
      (expect (= (landing-page) (:body response)))))
  (testing "invalid route"
    (let [req (mock/request :get "/whatever")
          response (webapp req)]
      (expect (= 404 (:status response))))))


(def ^:private mocked-post-request
  (-> (mock/request :post "/scramble")
      (mock/content-type "application/x-www-form-urlencoded")))


(deftest endpoint-test
  (testing "well-formed POST request with valid params"
    (let [response (webapp (-> mocked-post-request
                               (mock/body (form-encode {:letters "abc" :word "abc"}))))]
      (expect (= 200 (:status response)))))
  (testing "parameterless POST request"
    (let [response (webapp (-> mocked-post-request
                               (mock/body (form-encode {}))))]
      (expect (= 400 (:status response)))))
  (testing "well-formed POST request with invalid params"
    (let [response (webapp (-> mocked-post-request
                               (mock/body (form-encode {:letters "" :word "  "}))))]
      (expect (= 422 (:status response))))))


;; (run-tests)

;; Testing on cmd via curl
;; 1 - Well formed request with valid data
;; curl -i -H "Accept:application/transit+json" -X POST localhost:3000/scramblie -F "letters=abc" -F "word=abc"
;; 200

;; 2 - Well formed request with invalid data (empty strings)
;; curl -i -H "Accept:application/transit+json" -X POST localhost:3000/scramblie -F "letters=" -F "word="
;; 422

;; 2 - Invalid request, params not set
;; curl -i -H "Accept:application/transit+json" -X POST localhost:3000/scramblie
;; 400
