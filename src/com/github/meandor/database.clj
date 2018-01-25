(ns com.github.meandor.database
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.logging :as log]
            ))

(defrecord FeaturesEndpoint [handler model-builder]
  component/Lifecycle
  (start [self]
    (log/info "-> starting FeaturesEndpoint")

    self)
  (stop [_]
    (log/info "<- stopping FeaturesEndpoint")))

(defn new-features-endpoint []
  (map->FeaturesEndpoint {}))

