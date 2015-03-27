(ns pi-taster.bbp
  (:require [clojure.math.numeric-tower :as math]
            [clojure.core.reducers :as r]
            ))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Bailey–Borwein–Plouffe formula for pi, 
;; vector approach with clojure.core.reducers
;; MEMORY expensive operation
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn pi-bbp
  "BBP Formula"
  ([nth]
    (pi-bbp 0 nth )
  )
  ([start end]
    (r/fold 1 
      (fn([] 0)([& r] (apply + r))) 
      (fn[i k]  (let [base (/ 1 (math/expt 16 k))
                         f1 (/ 4 (+ (* 8 k) 1))
                         f2 (/ 2 (+ (* 8 k) 4))
                         f3 (/ 1 (+ (* 8 k) 5))
                         f4 (/ 1 (+ (* 8 k) 6))]
                     (* base (- f1 f2 f3 f4))
                     )) (vec (range start end)))
     )
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;MEMORY efficient operation
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn factorial 
    ([n] 
        (factorial n 1M)) 
    ([n acc] 
        (if  (= n 0)   acc 
             (recur (dec n) (* acc n)))))

(defn pi-memory-efficient 
  ([n] (pi-memory-efficient 1 n 0))
  ([i n sum] (if (= i (+ 1 n))
              (- sum 3M)
              (recur (inc i) n (+ sum (with-precision (+ 100 n ) (/
                                                          (let [fact (factorial i)] (* i (.pow (BigDecimal. "2") i)  fact fact))
                                                          (factorial (* 2 i))))))
              )))

(pi-memory-efficient 100)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; O (n^2) by Fabrice Bellard
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn pi-FB 
  ([n] (pi-FB 0 n 0))
  ([i n sum] (if (= i (+ 1 n))
              (* (/ (.pow (BigInteger. "2") 6)) sum)
              (recur (inc i) n (+ sum (with-precision (+ 100 n) (* (/ (if (even? i) 1 -1) (.pow (BigDecimal. "2") (* 10 i))) 
                                                (+ 
                                                  (- (/ (.pow (BigInteger. "2") 5) (+ (* 4 i) 1)))
                                                  (- (/ 1 (+ (* 4 i) 3))) 
                                                  (/ (.pow (BigInteger. "2") 8) (+ (* 10 i) 1)) 
                                                  (- (/ (.pow (BigInteger. "2") 6) (+ (* 10 i) 3))) 
                                                  (- (/ (* 2 2) (+ (* 10 i) 5))) 
                                                  (- (/ (* 2 2) (+ (* 10 i) 7))) 
                                                  (/ (+ (* 10 i) 9))
                                                  ))))))))

(pi-FB 1000)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;save forms appraoch
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- bbpPI-forms-to-file [start end] 
  "BBP Formula to a array of forms"
 (let [f (str "out/" start "-" end ".out")] 
   (do (spit f
        (reduce (fn[i k]  
                  (conj i `(* (/ 1 (math/expt 16 ~k))
                             ~(- (/ 4 (+ (* 8 k) 1)) (/ 2 (+ (* 8 k) 4)) (/ 1 (+ (* 8 k) 5)) (/ 1 (+ (* 8 k) 6)))
                             ))) 
          [] (range start end)))
     f)))


;;(time (println (.pow (BigDecimal. "16") 1000)))
;;(time (println (.pow (BigDecimal. "16") 1000)))

#_(time (println (r/fold 
                    (fn([] 1M)([& r] (apply * r))) 
                    (fn[i k] (* i 16)) (vec (range 100000000)))
         )
)

;(bbpPI-forms-to-file 100000000 100000001)
;(time (println (nth (iterate (fn [k] (* k (/ 1 16))) 1) 1000)))

(defn- bbpPI-forms-from-file
  "BBP Formula, eval the forms in the file"
  [path-name]
  (let [col (with-open 
             [r (java.io.PushbackReader. (java.io.FileReader. (java.io.File. path-name)))]
             (read r))]
    (r/fold 1 
      (fn([] 0)([& r] (apply + r))) 
      (fn[i k] (eval k)) (vec col)))
    )
