import random

funcNamePool = ["f", "g", "h"]
paraNamePool = ["x", "y", "z"]

funcPool = []
funcParaNumMap = {}
funcParasMap = {}
funcNum = 0

def g_funcExpr():
    global funcPool
    global funcParaNumMap
    global funcParasMap
    global funcNum

    funcPool = []
    funcParaNumMap = {}
    funcParasMap = {}
    funcNum = 0

    deepNum = random.randint(0,1)
    string = ""
    funcNum = random.randint(0, 3)
    string = string + str(funcNum) + '\n'
    names = random.sample(funcNamePool, funcNum)
    funcPool.extend(names)
    for i in range(funcNum):
        name = random.choice(names)
        names.remove(name)
        paraNum = random.randint(1, 3)
        paraPool = random.sample(paraNamePool, paraNum)
        funcParaNumMap[name] = paraNum
        funcParasMap[name] = paraPool

        string = string + name + g_whitespace() + ' (' + ' ,'.join(paraPool) + " ) = "
        string = string + g_expr(deepNum, True, name) + '\n'
    return string


def g_expr(deepNum, is_funcExpr=False, name='bug'):
    termNum = random.randint(1, 3)
    result = g_whitespace() + g_add_sub() + g_term(deepNum, is_funcExpr, name) + g_whitespace()

    for i in range(termNum - 1):
        if random.random() < 0.5:
            result = result + g_whitespace() + '+' + g_whitespace() + g_term(deepNum, is_funcExpr, name)
        else:
            result = result + g_whitespace() + '-' + g_whitespace() + g_term(deepNum, is_funcExpr, name)            
    return result

def g_term(deepNum, is_funcExpr=False, name='bug'):
    factorNum = random.randint(1, 3)
    result = g_whitespace() + g_add_sub() + g_factor(deepNum, is_funcExpr, name)
    
    for i in range(factorNum - 1):
        result = result + g_whitespace() + '*' + g_whitespace() + g_factor(deepNum, is_funcExpr, name)
    return result

def g_factor(deepNum, is_funcExpr=False, name='bug'):
    global funcNum
    
    if is_funcExpr == False:
        if deepNum > 0:
            r = random.random()
            if r < 0.2 and funcNum > 0:
                return g_defFuncFactor(deepNum - 1)
            elif r < 0.4:
                return g_exprFactor(deepNum - 1)
            elif r < 0.6:
                return g_expFactor(deepNum - 1)
            elif r < 0.7:
                return g_numFactor()
            elif r < 0.9:
                return g_dxFactor(deepNum - 1)
            else:
                return g_varFactor()
        else:
            if random.random() < 0.5:
                return g_varFactor()
            else:
                return g_numFactor()
    else:
        if deepNum > 0:
            r = random.random()
            if r < 0.3:
                return g_numFactor()
            elif r < 0.6:
                return g_varFactor(is_funcExpr, name)
            elif r < 0.9:
                return g_expFactor(deepNum - 1, is_funcExpr, name)
            else:
                return g_exprFactor(deepNum - 1, is_funcExpr, name)
        else:
            if random.random() < 0.5:
                return g_numFactor()
            else:
                return g_varFactor(is_funcExpr, name)

def g_varFactor(is_funcExpr=False, name='bug'):
    global funcParasMap

    if not is_funcExpr:
        return 'x' + g_whitespace() + g_exponent()
    else:
        paraPool = funcParasMap[name]
        var = random.choice(paraPool)
        return var + g_whitespace() + g_exponent()

def g_numFactor():
    return g_signed_integer()

def g_exprFactor(deepNum, is_funcExpr=False, name='bug'):
    return '(' + g_expr(deepNum, is_funcExpr, name) + ')' + g_whitespace() + g_exponent()

def g_expFactor(deepNum, is_funcExpr=False, name='bug'):
    return 'exp' + g_whitespace() + '(' + g_whitespace() + g_factor(deepNum, is_funcExpr, name) + g_whitespace() + ')'  + g_exponent()

def g_defFuncFactor(deepNum):
    global funcPool
    global funcParaNumMap

    name = random.choice(funcPool)
    paraNum = funcParaNumMap[name]
    actParas = []
    for i in range(paraNum):
        actParas.append(g_factor(deepNum))
    return name + '(' + ','.join(actParas) + ')'

def g_dxFactor(deepNum):
    if random.random() < 0.5:
        return 'dx' + g_whitespace() + '(' + g_whitespace() + g_dxFactor(deepNum) + g_whitespace() + ')'    
    else:
        return 'dx' + g_whitespace() + '(' + g_whitespace() + g_expr(deepNum) + g_whitespace() + ')' 

def g_exponent():
    r = random.random()
    if r < 0.6:
        return '^' + g_whitespace() + '+' + g_whitespace() + str(random.randint(0, 3))
    elif r < 0.8:
        return '^' + g_whitespace() + '+' + g_whitespace() + str(random.randint(4, 8))
    else:
        return ''

def g_signed_integer():
    return g_add_sub() + g_unsigned_integer() 

def g_unsigned_integer():
    r = random.random()
    if r < 0.15:
        return '0'
    elif r < 0.3:
        return '1'
    elif r < 0.8:
        return ''.join(random.choices('0123456789', k=random.randint(1, 3)))  
    else:
        return ''.join(random.choices('0123456789', k=random.randint(4, 8)))  

def g_whitespace():
    r = random.random()
    if r < 0.1:
        return ' ' * random.randint(1, 2)
    elif r < 0.2:
        return '\t' * random.randint(1, 2)
    else:
        return ''

def g_add_sub():
    if random.random() < 0.7:
        return random.choice(['+', '-']) + g_whitespace()
    else:
        return ''

def is_valid_expression(expr):
    if len(expr.replace(' ', '').replace('\t', '')) > 400:
        return "0"
    else: 
        return expr

funcDeclare = g_funcExpr()
print(funcDeclare, end="")
expr = g_expr(3)
print(expr, end="")

