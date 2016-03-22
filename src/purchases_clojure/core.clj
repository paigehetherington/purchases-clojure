(ns purchases-clojure.core
  (:require [clojure.string :as str]
            [clojure.walk :as walk]
            [clojure.pprint :as pp]) ; to print nicely, not as a string line
  (:gen-class))

(def categories (atom []))

(defn -main []
  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line]
                          (str/split line #","))
                       purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn [line]
                          (apply hash-map (interleave header line)))
                       purchases)
        purchases (walk/keywordize-keys purchases)
   
        _ (println "Please type a category and hit enter.") ; to print within let stmt
        text (read-line)
        purchases (filter (fn [line]
                            (= (:category line) text))
                          purchases)] 
    (spit "filtered_purchases.edn" (with-out-str (pp/pprint purchases))) ; replace pr-string to print nicely
    purchases))


  
