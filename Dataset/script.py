import csv
import random
from faker import Faker

fake = Faker()

records = 1000000
print("Sto eseguendo %d records\n" % records)

fieldnames = ['id', 'name', 'surname', 'email', 'skill1', 'skill2', 'skill3', 'level1', 'level2', 'level3']
writer = csv.DictWriter(open("dataset.csv", "w"), fieldnames=fieldnames)

skill = ['Java', 'C', 'HTML', 'Python', 'React', 'Node', 'C++', 'javascript',
         'CSS', 'Ruby', 'C#', 'Android', 'SQL', 'PHP']

names = []
level = []
for n in range(records):
    names.append(fake.name())
writer.writerow(dict(zip(fieldnames, fieldnames)))
for i in range(0, records):
    add1 = random.choice(skill)
    add2 = random.choice(skill)
    while add1 == add2:
        add2 = random.choice(skill)
    add3 = random.choice(skill)
    while add1 == add3 or add2 == add3:
        add3 = random.choice(skill)
    gener = random.choice(names).split()
    nome = gener[0]
    cognome = gener[1]
    email = nome[0].lower()+"." + cognome.lower()+str(i+6) + "@gmail.com"

    writer.writerow(dict([
        ('id', i+6),
        ('name', nome),
        ('surname', cognome),
        ('email', email),
        ('skill1', add1),
        ('skill2', add2),
        ('skill3', add3),
        ('level1', str(random.randint(0, 5))),
        ('level2', str(random.randint(0, 5))),
        ('level3', str(random.randint(0, 5)))]))

print("finito")
