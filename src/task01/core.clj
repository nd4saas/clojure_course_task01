(ns task01.core
  (:require [pl.danieljanus.tagsoup :refer :all])
  (:gen-class))

(defn inner-tags [tag] (filter #(vector? %) tag))
(defn get-attr [tag attr] (get (nth tag 1) attr))
(defn update-acc [acc tag]  (if (= :a (nth tag 0)) (conj acc tag) acc))
(defn find-anchors [tag]
  (loop [queue (list tag) acc ()]
		(if (empty? queue) acc
			(let [head (first queue)]
				(recur (concat (rest queue) (inner-tags head)) (update-acc acc head))))))

  
(defn get-links []
  (let [data (parse "clojure_google.html")]
    (into [] (map #(get-attr % :href) (filter #(= "l" (get-attr % :class)) (find-anchors data))))))

(defn -main []
  (println (str "Found " (count (get-links)) " links!")))
