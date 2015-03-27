(ns pi-taster.bbpmatrix
  (:refer-clojure :exclude [* - + == /]))

;;TODO, see if "LU fatorization" is implemented

(use 'clojure.core.matrix)
(use 'clojure.core.matrix.operators)  ;; for +, -, * etc.
;(set-current-implementation :vectorz) ; don't support for arbitary percision...
(set-current-implementation :persistent-vector) 

(defn coe-vec [func n] 
  (vec (map func (range n))))
(defn pi-val [n]
  (esum (inner-product 
          (matrix [
                   (coe-vec (fn [k] (/ 4 (+ (* 8 k) 1))) (inc n))
                   (coe-vec (fn [k] (- 0 (/ 2 (+ (* 8 k) 4)))) (inc n))
                   (coe-vec (fn [k] (- 0 (/ 1 (+ (* 8 k) 5)))) (inc n))
                   (coe-vec (fn [k] (- 0 (/ 1 (+ (* 8 k) 6)))) (inc n))
                  ])
           (matrix  (take (inc n) (iterate (fn [k] (* k (/ 1 16))) 1)))
           )))

(time (pi-val 1000))

#_(time (let [n (inc 1000)] (matrix :persistent-vector [
                                    (coe-vec (fn [k] (/ 4 (+ (* 8 k) 1))) n)
                                    (coe-vec (fn [k] (- 0 (/ 2 (+ (* 8 k) 4)))) n)
                                    (coe-vec (fn [k] (- 0 (/ 1 (+ (* 8 k) 5)))) n)
                                    (coe-vec (fn [k] (- 0 (/ 1 (+ (* 8 k) 6)))) n)
                                    ])))




;(transpose (matrix :sequence (take (inc 10) (iterate (fn [k] (* k (/ 1 16))) 1))))