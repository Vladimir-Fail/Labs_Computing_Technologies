
def Crate_List ():                          #к заданию 1
    return [i for i in range(45, 7, -3)]    #функция возвращает список чисел от 45 до 7 с шагом -3

def Create_Set_from_list(list):             #к заданию 2
    result = set()                          #создаем пустое множество
    for item in list:
        if (item>=0 and item<=10): 
            result.add(item)                #добавляем в него элементы от 0 до 10 включительно
        
        if (len(result) == 11): 
            break       #прекращаем выполнение функции если множество полностью заполнено всеми значениями от 0 до 10

    return result

def Create_List_from_dict(dict):    #к заданию 3
    listValues = list(dict.values())      #получаем список listValues из значений словаря dict
    return listValues

def Find_equals(listA, listB):  #к заданию 4 
    flag = True     #данная переменная будет отслеживать выполнение условия. В случае наличия общих элементов будет false
    i = 0 
    while(flag and i<len(listA)):

        j= 0
        while (flag and j < len(listB)):
            if (listB[j] == listA[i]):
                flag = False
            j+=1
        
        i+=1
    return (not flag)

def Find_area(listA, listB):                            #к заданию 5 
    dlina = len(listA)                                  #количество элементов в списках listA и listB
    area = set()                                        #создаем пустое множество
    for i in range(dlina):
        area.add(listA[i]*listB[i])                     #заполняем множество значениями площадей прямоугольников
   
    alist = sorted(list(area))                          #создаем список с значениями площадей в отсортированном виде
    if (len(alist) % 2 == 0):                           #нахождение медианы при четном количестве площадей
        median = (alist[(len(alist)//2) - 1] + alist[ (len(alist)//2)] ) / 2
    else:
        median = alist[len(alist)//2]                   #нахождение медианы при нечетном количестве площадей
    
    area = set(filter( lambda x: x>= median , area))    #удаляем из множества значения меньше медианы

    areasumm = 0
    for item in area:
        areasumm += item                                #находим сумму оставшихся площадей
    
    return areasumm/len(listA)                          #возвращаем сумму площадей поделенную на длину исходного списка
    
def Clear_dict(dict):                                   #к заданию 6
    
    dictvalues = list(dict.keys())  #получаем список ключей словаря
    minKey = min(dictvalues)   #получаем ключ с минимальным значением
    maxKey = max(dictvalues)                               #получаем ключ с максимальным значением

    average = (minKey+ maxKey)/2                              #получаем среднее арифметическое значение между максимальным и минимальным ключем
    remainkeys = filter(lambda x: x>= average , dict)   #получаем объект типа filter в котором ключи, которые больше среднего
    newdic = {key: dict[key] for key in remainkeys}     #создаем новый словарь используя только ключи remainkeys и значения из исходного словаря
    return newdic
    
def Date_from_seconds(seconds): #к заданию 7 
    
    days = seconds//86400 #получаем количество целых дней в исходном количестве секунд
    seconds-=(86400*days) #вычитаем дни из исходного количества секунд

    hours = seconds//3600 #получаем количество часов
    seconds-=(3600*hours)

    minutes = seconds//60 #получаем количество минут
    seconds-= (60*minutes)
    return days , hours , minutes, seconds #возвращаем кортеж значений 


'''к заданию 1'''
list1 = Crate_List() 
print('#1 ', list1) #выводим в консоль полученый список

'''к заданию 2'''
print('\n#2 result = ', Create_Set_from_list(list1)) #выводим множество полученное из значений списка list1

'''к заданию 3'''
dic = {x: (x+2)**3 for x in range(5, 0, -1)} #создаем произвольный словарь
list2 = Create_List_from_dict(dic) #получаем список
print('\n#3 ', list2) #выводим список
print('sorted list' , sorted(list2)) #выводим отсортированный список

'''к заданию 4'''
list3 = [x for x in range(9)] #создаем два списка с числами
list4 = [y for y in range(5, 13)]
print('\n#4 list3:', list3, "\nlist2:", list4) #выводим исходные списки
print("result = ", Find_equals(list3, list4)) #выводим: есть ли общий элемент в этих списках

'''к заданию 5'''
list5 = [x for x in range(1, 60, 3)] #создаем два списка с числами и одинаковым количеством элементов
list6 = [x for x in range(60, 1, -3)]
result5 = Find_area(list5, list6) #выполняем функцию Find_area()
print('\n#5 result = ', result5) #выводим результат (число)

'''к заданию 6'''
dict6 = {x: str(x**2) for x in range(30, 10, -2)} #создаем произвольный словарь (ключи - числа, значения - строки)
dict7 = Clear_dict(dict6) #фильтруем словарь и записивыем результат в другой словарь
print('\n#6 исходный dict = ', dict6, '\nresult = ', dict7) #выводим в консоль словари

'''к заданию 7'''
seconds = 86400 #задаем произвольное количество секунд
d,h,m,s = Date_from_seconds(seconds) #получаем значение дней, часов, минут, секунд 
print('\n#7 current date = ' , f'{d}/{h}/{m}/{s}') #выводим результат







