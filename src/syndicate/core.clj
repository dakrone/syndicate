(ns syndicate.core
  (:require [org.danlarkin.json :as json])
  (:require [clj-http.client :as http])
  (:require [opennlp.nlp :as nlp])
  (:require [opennlp.tools.filters :as filters]))


(def *apikey* "YOUR-API-KEY")

(if (= *apikey* "YOUR-API-KEY")
  (do
    (println "Replace 'YOUR-API-KEY' in src/syndicate/core.clj with your api key. You can get an API key here: http://words.bighugelabs.com/api.php")
    (flush)))

(def *bighuge* (str "http://words.bighugelabs.com/api/2/" *apikey* "/"))
(def *debug* false)

(defn get-words
  [word]
  (if-not (nil? word)
    (try
      (json/decode-from-str (:body (http/get (str *bighuge* word "/json"))))
      (catch Exception e
        nil))))


(defn replace-noun
  [word]
  (let [candidates (:syn (:noun (get-words word)))
        new-word (get candidates (rand-int (count candidates)))]
    (if *debug*
      (println "swapping" word "for" new-word))
    new-word))


(defn replace-verb
  [word]
  (let [candidates (:syn (:verb (get-words word)))
        new-word (get candidates (rand-int (count candidates)))]
    (if *debug*
      (println "swapping" word "for" new-word))
    new-word))


(defn replace-word
  [word tag]
  (if (> (count (str word)) 2)
    (let [new-word (cond
                     (re-matches #"N.*" (str tag)) (replace-noun (str word))
                     (re-matches #"V.*" (str tag)) (replace-verb (str word))
                     :else word)]
      (if new-word
        new-word
        word))
    word))


(defn replace-sentence
  [sentence]
  (map #(replace-word (first %) (second %)) sentence))


(defn replace-text
  [text]
  (let [get-sentences (nlp/make-sentence-detector "models/EnglishSD.bin.gz")
        tokenize      (nlp/make-tokenizer "models/EnglishTok.bin.gz")
        pos-tag       (nlp/make-pos-tagger "models/tag.bin.gz")
        sentences     (get-sentences text)
        _             (if *debug* (println sentences))
        tokens        (map tokenize sentences)
        _             (if *debug* (println tokens))
        taggedwords   (map pos-tag tokens)
        _             (if *debug* (println taggedwords))
        newwords      (map replace-sentence taggedwords)
        _             (if *debug* (println newwords))]
    (reduce #(str %1 " " %2) (flatten newwords))))

