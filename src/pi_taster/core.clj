(ns pi-taster.core
  (:require 
    [pi-taster.bbp :as bbp]
    [pi-taster.bbpmatrix :as bbpm]
    [clojure.data.json :as json]
    [clojure.java.io :as io]
            )
   (:gen-class 
     :main true 
     :methods [ ^:static [getStatAt [String] String ]]))
(use '[clojure.string :only (replace-first)])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; API
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(declare sorted-freqency-map)
(defn -getStatAt
 [nth-of-pi]
 (json/write-str
   (reduce (fn [i e] (conj i {:name (name(key e)) :freq (val e)}) ) [] (sorted-freqency-map 3 (Integer/parseInt nth-of-pi)))))

(defn -main
  "Main function"
  [& args]
  (spit (str "out." (Integer/parseInt (first args)) ".txt")
    (json/write-str
      (reduce (fn [i e] (conj i {:name (name(key e)) :freq (val e)}) ) [] (sorted-freqency-map 3 (Integer/parseInt (first args))))
                       )))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Retrieve PI value
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn pi-precision-to [preci func]
  (with-precision preci :rounding FLOOR (* 1M func )))

(time (pi-precision-to 1000 ( bbpm/pi-val 1000)))
(time (pi-precision-to 1000 (bbp/pi-bbp 0 (+ 1 1000))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; String Manipulation
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn format-digit-string [string]
  "return a function needed an index and a prefix for the index"
  (fn [index freq-prefix] 
    (reduce (fn[i e] 
                (conj i 
                  {(key e) {(str freq-prefix  index) (count (val e))}})) 
        (sorted-map) (group-by str (vec string)))))

 ;((format-digit-string "3.14159265358979323846264338327") 1 "ff")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Services Integration
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn- sorted-freqency-map [num-of-fragments fragment-length]
  (let [nth-digit (* num-of-fragments fragment-length)
              pi-in-str (replace-first (str (pi-precision-to nth-digit (bbp/pi-bbp 0 (+ 1 nth-digit)))) "." "")
              ]
          (->> (range num-of-fragments) 
            (map (fn [index] (let [f-str (subs pi-in-str (* index  fragment-length) (* (+ index 1)  fragment-length))
                                   format-fn (format-digit-string f-str)]
                               (format-fn index "f"))))
            (reduce (fn[result-map m] (merge-with merge result-map m)))
            )
          ))

;(sorted-freqency-map 3 100)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; REPL TESTING plan
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
#_(map #(* 1000 %) (loop [i 1
                         stop (/ 1000000000 (*  3000))
                         result []
                         ]
                   (if (> i stop)
                     result
                     (recur (* i 2) stop (conj result i) ))))
;;(1000 2000 4000 8000 16000 32000 64000 128000 256000 512000 1024000 2048000 4096000 8192000 16384000 32768000 65536000 131072000 262144000)
;;(3000 6000 12000 24000 48000 96000 192000 384000 768000 1536000 3072000 6144000 12288000 24576000 49152000 98304000 196608000 393216000 786432000)

(defn- gen-files
  [& args]
  (do
   (map (fn[ii] (do 
                (spit (str "out." (* 3 ii) ".txt") 
                    (with-out-str (do (println "working on: 3 * " ii) 
                                    (time (json/pprint
                                                (reduce (fn [i e] (conj i {:name (name(key e)) :freq (val e)}) ) [] (sorted-freqency-map 3 ii)))))) )
                         ii )) args)
  
 ))
;(gen-files 1000)

