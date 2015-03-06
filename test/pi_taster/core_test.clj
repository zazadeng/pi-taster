(ns pi-taster.core-test
  (:require [clojure.test :refer :all]
            [pi-taster.core :refer :all]))

(deftest check-format
  (testing "Check formating"
    (is (= {"." {"ff1" 1}, "1" {"ff1" 2}, "2" {"ff1" 4}, "3" {"ff1" 7}, "4" {"ff1" 3}, "5" {"ff1" 3}, "6" {"ff1" 3}, "7" {"ff1" 2}, "8" {"ff1" 3}, "9" {"ff1" 3}}
           ((format-digit-string "3.14159265358979323846264338327") 1 "ff"))))
  )

(check-format)

(deftest check-getStatAt
  (testing "Check final stats json"
    (is (= "[{\"name\":\"0\", \"freq\":{\"f2\":7, \"f1\":11, \"f0\":8}},\n {\"name\":\"1\", \"freq\":{\"f2\":10, \"f1\":12, \"f0\":8}},\n {\"name\":\"2\", \"freq\":{\"f2\":11, \"f1\":12, \"f0\":12}},\n {\"name\":\"3\", \"freq\":{\"f2\":11, \"f1\":8, \"f0\":12}},\n {\"name\":\"4\", \"freq\":{\"f2\":15, \"f1\":12, \"f0\":10}},\n {\"name\":\"5\", \"freq\":{\"f2\":7, \"f1\":12, \"f0\":8}},\n {\"name\":\"6\", \"freq\":{\"f2\":16, \"f1\":6, \"f0\":9}},\n {\"name\":\"7\", \"freq\":{\"f2\":7, \"f1\":4, \"f0\":8}},\n {\"name\":\"8\", \"freq\":{\"f2\":9, \"f1\":13, \"f0\":12}},\n {\"name\":\"9\", \"freq\":{\"f2\":7, \"f1\":10, \"f0\":13}}]\n" 
          (-getStatAt "100")) "Result not equal")))

(check-getStatAt)

