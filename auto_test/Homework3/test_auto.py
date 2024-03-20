from sympy import simplify, sympify
import os
from typing import List
import sys
import time
from func_timeout import func_set_timeout
import func_timeout



yyb = "E:/Course/Term4/OO/auto_test/Homework3/jar/yyb.jar"
zyt = "E:/Course/Term4/OO/auto_test/Homework3/jar/zyt.jar"
zhw = "E:/Course/Term4/OO/auto_test/Homework3/jar/zhw.jar"
zxd = "E:/Course/Term4/OO/auto_test/Homework3/jar/zxd.jar"

guang = "E:/Course/Term4/OO/auto_test/Homework3/jar/guang.jar"
heng = "E:/Course/Term4/OO/auto_test/Homework3/jar/heng.jar"
ji = "E:/Course/Term4/OO/auto_test/Homework3/jar/ji.jar"
quan = "E:/Course/Term4/OO/auto_test/Homework3/jar/quan.jar"
shu = "E:/Course/Term4/OO/auto_test/Homework3/jar/shu.jar"
xuan = "E:/Course/Term4/OO/auto_test/Homework3/jar/xuan.jar"
yang = "E:/Course/Term4/OO/auto_test/Homework3/jar/yang.jar"

jar_path: List[str] = [zyt, yyb, zxd, heng, ji, xuan, quan, shu, yang]
jar_name: List[str] = ["zyt", "yyb", "zxd", "heng", "ji", "xuan", "quan", "shu", "yang"]

jar_standard = zyt

data_generator_path = "E:/Course/Term4/OO/auto_test/Homework3/data_ganerate.py"

deal_defFunc_path = "E:/Course/Term4/OO/auto_test/Homework3/deal_defFunc.py"

####################################################################################
def delete_txt():
    txt_files = [f for f in os.listdir('txt') if f.endswith('.txt')]
    for file in txt_files:
        os.remove(os.path.join('txt', file))


def execute_run_with_timeout(data_num, person_num):
    try:
        run_java(jar_path[person_num], data_num, person_num)
        check_results(1, data_num, person_num)
    except func_timeout.exceptions.FunctionTimedOut:
        print('运行程序超时')
        sys.exit()
        

def execute_dataG_with_timeout(data_num):
    try:
        data_generate(data_num)
        run_standard_java(data_num)
        print('生成数据成功')
        return True
    except func_timeout.exceptions.FunctionTimedOut:
        print('生成数据超时')
        
        delete_txt()
        pass        
        return False
##############################################################################




###############################################################################
#             1:normal 0:special
def test_program(type, data_num, person_num):
    
    if type == 1:
        for i in range(data_num):
            print("data_" + str(i) + ":" + '-'*50)
            ready = execute_dataG_with_timeout(i)
            if not ready:
                continue
            for j in range(person_num):
                print(jar_name[j] + ":", end="")
                execute_run_with_timeout(i, j)
            print("-" * 57)
            delete_txt()
    else:
        print("start:")
        os.system('java -jar "{}" < special_in.txt > txt/special_standard.txt 2> txt/special_err.txt'.format(jar_standard))
        print("standard ready")
        for i in range(person_num):
                print(jar_name[i] + ":", end="")
                os.system('java -jar "{}" < special_in.txt > txt/special_{}_out.txt 2> txt/special_err.txt'.format(jar_path[i], jar_name[i]))
                check_results(0, -1, i)            

            
@func_set_timeout(20)
def data_generate(data_num):
    os.system('python "{}" > txt/{}_in.txt'.format(data_generator_path, data_num))    

@func_set_timeout(20)
def run_java(jar_path, data_num, person_num):
    os.system('java -jar "{}" < txt/{}_in.txt > txt/{}_{}_out.txt 2> txt/{}_err.txt'.format(jar_path, data_num, data_num, jar_name[person_num], data_num))

@func_set_timeout(20)
def run_standard_java(data_num):
    os.system('java -jar "{}" < txt/{}_in.txt > txt/{}_standard.txt 2> txt/{}_err.txt'.format(jar_standard, data_num, data_num, data_num))
    f_stand = open('txt/{}_standard.txt'.format(data_num), 'r')
    java_out_stand = f_stand.readline()
    strlen = len(java_out_stand.replace("\n", ""))
    print("standard_len: " + str(strlen))


# @func_set_timeout(5)
# def deal_defFunc(data_num):
#     os.system('python "{}" < txt/{}_in.txt > txt/{}_ans.txt'.format(deal_defFunc_path, data_num, data_num))

@func_set_timeout(20)
def check_results(type, data_num, person_num):
    if type == 1:
        f_out = open('txt/{}_{}_out.txt'.format(data_num, jar_name[person_num]), 'r')
    else :
        f_out = open('txt/special_{}_out.txt'.format(jar_name[person_num]), 'r')        
    java_out = f_out.readline().replace("\n", "")

    if type == 1:
        f_stand = open('txt/{}_standard.txt'.format(data_num), 'r')
    else:
        f_stand = open('txt/special_standard.txt', 'r')        
    java_out_stand = f_stand.readline().replace("\n", "")

    zero_expr = '0\n' + '(' + java_out + ') - (' + java_out_stand + ')'
    


    with open("txt/{}_{}_zero_in.txt".format(data_num, jar_name[person_num]), "w") as f:
        f.write(zero_expr)

    os.system('java -jar "{}" < txt/{}_{}_zero_in.txt > txt/{}_{}_zero_out.txt 2> txt/{}_{}_zero_err.txt'.format(jar_standard, data_num, jar_name[person_num], data_num, jar_name[person_num], data_num, jar_name[person_num]))
    
    f_zero = open('txt/{}_{}_zero_out.txt'.format(data_num, jar_name[person_num]), 'r')
    zero = f_zero.readline()
    zero = zero.replace(" ", "")
    if int(zero) == 0:
        print(True, end="")
    else:
        print(False)
        print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        print("orign_data:")
        if type == 1:
            f_in = open('txt/{}_in.txt'.format(data_num), 'r')
            lines = f_in.readlines()
            for i in range(len(lines)):
                print(lines[i], end="")       

        print("\nperson_out:")
        print(java_out)
        print("\nsimply_ans:")
        print(java_out_stand)
        sys.exit()

    strlen = len(java_out.replace("\n", ""))
    print("  len:" + str(strlen))
    

test_program(1, 1000, 2)

#test_program(0, -1, 2)







