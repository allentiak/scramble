(ns clojure-challenges.scramble
  (:require [clojure.string :as str]))

(defn rearrangable?
  "returns true if a portion of big-str characters can be rearranged to match small-str, otherwise returns false.
  Worst possible case scenario comparison.
  Only considers lowercase a-z characters."
  [big-str small-str]
  (if (nil? small-str) true
      (if (not (str/includes? big-str (str (first small-str)))) false
          (rearrangable? big-str (seq (rest small-str))))))

(comment
  (rearrangable? "ab" "b"))

(defn scramble?
  "returns true if a portion of big-str characters can be rearranged to match small-str, otherwise returns false.
  Efficient version.
  Only considers lowercase a-z characters."
  [big-str small-str]
  (if (or (= big-str nil) (= small-str nil)) false
    (if (= big-str small-str) true
      (if (< (count big-str) (count small-str)) false
        (if (str/includes? big-str small-str) true
          (rearrangable? big-str small-str))))))
