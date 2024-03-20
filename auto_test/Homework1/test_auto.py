from sympy import simplify, sympify
import os
import re
from typing import List

jar_path1 = "E:/Course/Term4/OO/auto_test/Homework1/guang.jar"
jar_path2 = "E:/Course/Term4/OO/auto_test/Homework1/heng.jar"
jar_path3 = "E:/Course/Term4/OO/auto_test/Homework1/ji.jar"
jar_path4 = "E:/Course/Term4/OO/auto_test/Homework1/quan.jar"
jar_path5 = "E:/Course/Term4/OO/auto_test/Homework1/shu.jar"
jar_path6 = "E:/Course/Term4/OO/auto_test/Homework1/xuan.jar"
jar_path7 = "E:/Course/Term4/OO/auto_test/Homework1/ming.jar"

jar_path: List[str] = [jar_path1, jar_path2, jar_path3, jar_path4, jar_path5, jar_path6, jar_path7]

def test_program():
    data = data_generate()
    for i in range(7):
        result = run_java(jar_path[i])
        print(str(i) + ":")
        check_results(data, result)

def data_generate():
    data_generator_path = "E:/Course/Term4/OO/auto_test/Homework1/data_ganerate.py"
    os.system('python "{}" > in.txt'.format(data_generator_path))
    f1 = open('in.txt', 'r')   
    line1 = f1.readline()
    line1 = line1.replace('\n', '')
    print("------------------------------------------------------------")
    print("Test Expression:", line1) 
    return line1

def run_java(jar_path):
    os.system('java -jar "{}" < in.txt > out.txt'.format(jar_path))
    f2 = open('out.txt', 'r')
    line2 = f2.readline()
    line2 = line2.replace('\n', '')
    print("Java Output:", line2)
    return line2

def check_results(data, result):
    data_no_leading_zero = re.sub(r'\b0+(\d+)', r'\1', data).replace(" ", '')

    expr1_simplified = simplify(sympify(data_no_leading_zero))
    expr2_simplified = simplify(sympify(result))
    
    expressions_equal = expr1_simplified.equals(expr2_simplified)
    print("Expressions Equal:", expressions_equal)
    
    if (not expressions_equal):
        sys.exit()
        


for i in range(500):
    test_program()







