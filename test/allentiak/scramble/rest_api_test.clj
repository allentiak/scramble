(ns allentiak.scramble.rest-api-test
  (:require
    [allentiak.scramble.rest-api :refer [webapp landing-page]]
    [clojure.test :refer [deftest is testing]]
    [ring.mock.request :as mock]))

(deftest webapp-smoke-test
  (testing "always valid route"
    (let [req (mock/request :get "/")
          response (webapp req)]
      (is (= 200 (:status response)))
      (is (= (landing-page) (:body response)))))

  (testing "invalid route"
    (let [req (mock/request :get "/whatever")
          response (webapp req)]
      (is (= 404 (:status response))))))
