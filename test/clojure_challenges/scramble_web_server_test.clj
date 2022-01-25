(ns clojure-challenges.scramble-web-server-test
  (:require [clojure.test :refer [deftest is testing]]
            [ring.mock.request :as mock]
            [clojure-challenges.scramble :refer [scramble?]]
            [clojure-challenges.scramble-web-server :refer [default-page web-server]]))

(deftest scramble-web-server-test
  (testing "always valid route"
    (let [response (web-server (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) (default-page)))))

  (testing "invalid route"
    (let [response (web-server (mock/request :get "/whatever"))]
      (is (= (:status response) 404))))

  (testing "valid route"
    (let [response (web-server (mock/request :post "/scramble" {:letters "abc" :word "a"}))]
      (is (= (:status response) 200))
      (is (= (:body response) (str (scramble? "abc" "a")))))))
