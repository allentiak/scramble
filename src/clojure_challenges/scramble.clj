(ns clojure-challenges.scramble
  (:require [clojure.string :as str]))

(defn scramble?
  "returns true if a portion of big-str characters can be rearranged to match small-str, otherwise returns false.
  Only consider lowercase a-z characters."
  [big-str small-str]
  (if (or (= big-str nil) (= small-str nil)) false
    (if (= big-str small-str) true
      (if (str/includes? big-str small-str) true
        (if (< (count big-str) (count small-str)) false
          true)))))
