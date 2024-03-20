from sympy import simplify, sympify
import os
from typing import List
import sys
import time
from func_timeout import func_set_timeout
import func_timeout

yyb = "E:/Course/Term4/OO/auto_test/Homework2/jar/yyb.jar"
zyt = "E:/Course/Term4/OO/auto_test/Homework2/jar/zyt.jar"

jar_path: List[str] = [zyt, yyb]
jar_name: List[str] = ["zyt", "yyb"]

data_generator_path = "E:/Course/Term4/OO/auto_test/Homework2/data_ganerate.py"

deal_defFunc_path = "E:/Course/Term4/OO/auto_test/Homework2/deal_defFunc.py"

####################################################################################

@func_set_timeout(20)

def execute_run_with_timeout(data_num, person_num):
    try:
        run_java(jar_path[person_num], data_num, person_num)
        check_results(1, data_num, person_num)
    except func_timeout.exceptions.FunctionTimedOut:
        print('运行程序超时')

def execute_dataG_with_timeout(data_num):
    try:
        data_generate(data_num)
        deal_defFunc(data_num)
    except func_timeout.exceptions.FunctionTimedOut:
        print('解析函数超时')         
##############################################################################




###############################################################################
#             1:normal 0:special
def test_program(type, data_num, person_num):
    
    if type == 1:
        for i in range(data_num):
            print("data_" + str(i) + ":" + '-'*50)
            execute_dataG_with_timeout(i)
            print('生成数据&解析函数成功')
            for j in range(person_num):
                print(jar_name[j] + ":", end="")
                execute_run_with_timeout(i, j)
            print("-" * 57)
    else:
        print("start:")
        os.system('python "{}" < txt/special_in.txt > txt/special_ans.txt'.format(deal_defFunc_path))
        print('解析成功')
        for i in range(person_num):
                print(jar_name[i] + ":", end="")
                os.system('java -jar "{}" < txt/special_in.txt > txt/special_{}_out.txt 2> txt/special_err.txt'.format(jar_path[i], jar_name[i]))
                check_results(0, -1, i)            

            

def data_generate(data_num):
    os.system('python "{}" > txt/{}_in.txt'.format(data_generator_path, data_num))    

def run_java(jar_path, data_num, person_num):
    os.system('java -jar "{}" < txt/{}_in.txt > txt/{}_{}_out.txt 2> txt/{}_err.txt'.format(jar_path, data_num, data_num, jar_name[person_num], data_num))

def deal_defFunc(data_num):
    os.system('python "{}" < txt/{}_in.txt > txt/{}_ans.txt'.format(deal_defFunc_path, data_num, data_num))

def check_results(type, data_num, person_num):
    if type == 1:
        f_out = open('txt/{}_{}_out.txt'.format(data_num, jar_name[person_num]), 'r')
    else :
        f_out = open('txt/special_{}_out.txt'.format(jar_name[person_num]), 'r')        
    java_out = f_out.readline()

    if type == 1:
        f_ans = open('txt/{}_ans.txt'.format(data_num), 'r')
    else:
        f_ans = open('txt/special_ans.txt', 'r')        
    dealt_ans = f_ans.readline()

    out_simplified = sympify(java_out)
    ans_simplified = sympify(dealt_ans)
    
    expressions_equal = out_simplified.equals(ans_simplified)
    print(expressions_equal, end="")
    strlen = len(java_out.replace("\n", ""))
    print("  len:" + str(strlen))
    
    if not expressions_equal:
        print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        print("orign_data:")
        if type == 1:
            f_in = open('txt/{}_in.txt'.format(data_num), 'r')
            lines = f_in.readlines()
            for i in range(len(lines)):
                print(lines[i], end="")       

        print("\nperson_out:")
        print(java_out)
        print("simply_ans:")
        print(dealt_ans)
        sys.exit()



test_program(1, 100, 4)

#test_program(0, -1, 4)







