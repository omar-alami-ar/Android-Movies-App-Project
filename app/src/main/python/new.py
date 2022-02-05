import pandas as pd 
import numpy as np
from os.path import dirname, join
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import CountVectorizer

def main(titre):
    filename = join(dirname(__file__), "movie_data.csv")
    df = pd.read_csv(filename)


    df.head(3)

    columns=['Actors','Director','Genre','Title']


    df[columns].head(3)

    df[columns].isnull().values.any()
    df['important_features']=get_important_features(df)

    df.head(3)

    cm= CountVectorizer().fit_transform(df['important_features'])
    cs= cosine_similarity(cm)

    print(cs)

    cs.shape

    title= titre

    movie_id = df[df.Title == title]['Rank'].values[0]

    scores = list(enumerate(cs[movie_id]))


    sorted_scores = sorted(scores, key= lambda x:x[1], reverse= True)
    sorted_scores= sorted_scores[1:]

    print(sorted_scores)

    j=0
    titles =""
    for item in sorted_scores:
        movie_title = df[df.Rank == item[0]]['Title'].values[0]
        titles += movie_title+";"
        j = j+1
        if j>6:
            break
    #print(len(titles))
    return titles 



def get_important_features(data):
	important_featues = []
	for i in range(0, data.shape[0]):
		important_featues.append(data['Actors'][i]+' ' +data['Director'][i]+' '+data['Genre'][i]+' '+data['Title'][i])
	return important_featues




   
















