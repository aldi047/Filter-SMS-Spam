import joblib
import pandas as pd
from os.path import dirname, join
from Sastrawi.Stemmer.StemmerFactory import StemmerFactory

def main(input):
    from os.path import dirname, join
    filename = join(dirname(__file__), "classifier_nb.best_estimator_sms_spam.sav")
    with open(filename, 'rb') as file:
        loaded_model = joblib.load(file)
        factory = StemmerFactory()
        stemmer = factory.create_stemmer()

        final_string = []
        s = ""
        data_to_predict = pd.Series(input)
        for sentence in data_to_predict.values:

            filteredSentence = []
            EachReviewText = ""
            s = (stemmer.stem(sentence))
            filteredSentence.append(s)

            EachReviewText = ' '.join(filteredSentence)
            final_string.append(EachReviewText)

        hasil = loaded_model.predict(final_string)
        return hasil[0]
