from sympy import symbols, simplify, exp 
import re


def cleanStr(string):
    newStr = re.sub(r'\b0+(\d+)', r'\1', string)
    
    newStr = newStr.replace(" ", '')
    newStr = newStr.replace('^', "**")
    return newStr    


x, y, z = symbols('x y z')

n = int(input())

for _ in range(n):

    definition = input()

    definition = cleanStr(definition)
    
    string = "def "
    definition = string + definition.replace('=', ":return ")

    exec(definition)

expression = input()

expression = cleanStr(expression)

exec("expression = " + expression)

print(str(expression).replace("**", '^'))

