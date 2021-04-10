import pandas as pd
import missingno as msno
from sklearn import model_selection
from sklearn.ensemble import BaggingClassifier
from sklearn.tree import DecisionTreeClassifier
import pickle

data = pd.read_csv(r'C:\Users\User\Documents\GitHub\Python\heart_disease\framingham01.csv')
names = ['male', 'age', 'currentSmoker', 'cigsPerDay', 'BPMeds', 'prevalentStroke', 'prevalentHyp', 'diabetes', 'totChol','sysBP','diaBP','BMI','heartRate','glucose','Target']

msno.matrix(data)
from sklearn.impute import SimpleImputer
imputer = SimpleImputer(strategy='most_frequent')
imputer.fit(data)
imputed_data = imputer.transform(data)
imputed_data = pd.DataFrame(imputed_data,columns=data.columns)
print(imputed_data)

array = imputed_data.values
X = array[:,0:14]
Y = array[:,14]

seed = 7
kfold = model_selection.KFold(n_splits=10, random_state=seed, shuffle=True)
cart = DecisionTreeClassifier()
num_trees = 100
model = BaggingClassifier(base_estimator=cart, n_estimators=num_trees, random_state=seed)
model.fit(X,Y)
results = model_selection.cross_val_score(model, X, Y, cv=kfold)

print(results.mean())

pickle.dump(model,open('model.pkl','wb'))
