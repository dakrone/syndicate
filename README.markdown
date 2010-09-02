# syndicate

Fun with NLP and synonyms

## Usage

Replace the apikey var in src/syndicate/core.clj with your api key from http://words.bighugelabs.com/api.php

    lein deps
    lein repl

    (use 'syndicate.core)
    (replace-text "Once she got that second acceptance, she began to show little ways that her identification with Helga wasn't as complete as she said. She felt free, bit by bit, to acustom me to a personality that wasn't Helga's, but her own.")

    "Once she got that second toleration , she began to render little structure that her personal identity with Helga was n't as complete as she said . She matte free , bit by public presentation , to acustom me to a attribute that was n't Helga 's , but her own ."

Probably not the best example sentence, but the source is easy enough to figure out how it works.

## Installation

Why on earth would you want to install this? It's just for fun.

## License

Copyright (C) 2010 Lee Hinman

Distributed under the BSD license.
