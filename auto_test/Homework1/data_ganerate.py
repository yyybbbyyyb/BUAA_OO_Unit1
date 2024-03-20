import random

def g_expr(deepNum):
    termNum = random.randint(1, 3)
    result = g_whitespace() + g_add_sub() + g_term(deepNum) + g_whitespace()

    for i in range(termNum - 1):
        if random.random() < 0.5:
            result = result + g_whitespace() + '+' + g_whitespace() + g_term(deepNum)
        else:
            result = result + g_whitespace() + '-' + g_whitespace() + g_term(deepNum)            
    return result

def g_term(deepNum):
    factorNum = random.randint(1, 3)
    result = g_whitespace() + g_add_sub() + g_factor(deepNum)
    
    for i in range(factorNum - 1):
        result = result + g_whitespace() + '*' + g_whitespace() + g_factor(deepNum)
    return result

def g_factor(deepNum):
    if deepNum > 0:
        return g_exprFactor(deepNum - 1)
    elif random.random() < 0.5:
        return g_numFactor()
    else:
        return g_varFactor()

def g_varFactor():
    return 'x' + g_whitespace() + g_exponent()

def g_numFactor():
    return g_signed_integer()

def g_exprFactor(deepNum):
    return '(' + g_expr(deepNum) + ')' + g_whitespace() + g_exponent()

def g_exponent():
    if random.random() < 0.7:
        return '^' + g_whitespace() + '+' + g_whitespace() + str(random.randint(0, 8))
    else:
        return ''

def g_signed_integer():
    return g_add_sub() + g_unsigned_integer() 

def g_unsigned_integer():
    if random.random() < 0.15:
        return '0'
    elif random.random() < 0.3:
        return '1'
    elif random.random() < 0.8:
        return ''.join(random.choices('0123456789', k=random.randint(1, 3)))  
    else:
        return ''.join(random.choices('0123456789', k=random.randint(4, 8)))  

def g_whitespace():
    if random.random() < 0.1:
        return ' ' * random.randint(1, 2)
    elif random.random() < 0.2:
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


expr = g_expr(1)
expr = is_valid_expression(expr)
expr = expr.replace('\n', '')  # 移除换行符
print(expr, end='')
