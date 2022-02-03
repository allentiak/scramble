(ns clojure-challenges.scramble-webserver-test
  (:require [clojure.test :refer [deftest is testing]]
            [ring.mock.request :as mock]
            [clojure-challenges.apis.scramble :refer [scramble?]]
            [clojure-challenges.scramble-webserver :refer [default-page webserver]]))

(deftest scramble-webserver-test
  (testing "always valid route"
    (let [response (webserver (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) (default-page)))))

  (testing "invalid route"
    (let [response (webserver (mock/request :get "/whatever"))]
      (is (= (:status response) 404))))

  (testing "valid route & valid request"
    (let [valid-request (mock/request :post "/scramble" {:letters "abc" :word "a"})
          response (webserver valid-request)]
      (is (= (:status response) 200))
      (is (= (:body response) (str (scramble? "abc" "a")))))))
