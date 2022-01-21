(ns clojure-challenges.scramble)

(defn scramble?
  "returns true if a portion of big-str characters can be rearranged to match small-str, otherwise returns false.
  Only consider lowercase a-z characters."
  [big-str small-str]
  (if (= big-str small-str) true
      (if (< (count big-str) (count small-str)) false
          true)))
