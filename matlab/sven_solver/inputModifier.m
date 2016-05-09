function [w,yz,ys,ES]= inputModifier(anzElementeMin,anzElementeMax,fmin,fmax,skin,w,yz,ys)
%inputModifier verändert den Output des Filereaders gemäss
%den Optionen
%anzElemente ist die Anzahl Elemente in einem ES
%fMin und fMax ist der Frequenzbereich für den ein ES gesucht wird

wMin=fmin*2*pi;
wMax=fmax*2*pi;
indexMin=1;%Zählvar
indexMax=length(w);%Zählvar
ESprop=[1,2 %Ersatzschaltbilder (Nummer,anz Elemente)
    2,2
    3,2
    4,2
    5,3
    6,3
    7,3
    8,3
    9,3
    10,4
    11,4
    12,4
    13,4
    14,3
    15,3
    16,3
    17,3
    18,4
    19,4
    20,4
    21,4];
    

%frequenzbereich einschränken
while w(indexMin)<wMin
    indexMin=indexMin+1;
end

while w(indexMax)>wMax
    indexMax=indexMax-1;
end

w=w(indexMin:indexMax);
ys=ys(indexMin:indexMax);
yz=yz(indexMin:indexMax);



%AnzElemente in ES einschränken
indexMin=1;
indexMax=13;
while ESprop(indexMin,2)<anzElementeMin
    indexMin=indexMin+1;
end
while (ESprop(indexMax,2)>anzElementeMax)&&(indexMin~=indexMax)
    indexMax=indexMax-1;
end
if indexMin~=indexMax
        ES=indexMin:indexMax;
end

    
if skin==1
    indexMin=14;
    indexMax=21;
    while ESprop(indexMin,2)<anzElementeMin
        indexMin=indexMin+1;
    end
    while (ESprop(indexMax,2)>anzElementeMax)&&(indexMin~=indexMax)
        indexMax=indexMax-1;
    end
    if indexMin~=indexMax
        ES=[ES,indexMin:indexMax];
    end
end



% ES1=struct('Nummer',1,'anzElemente',2,'Skin',0);
% ES2=struct('Nummer',2,'anzElemente',2,'Skin',0);
% ES3=struct('Nummer',3,'anzElemente',2,'Skin',0);
% ES4=struct('Nummer',4,'anzElemente',2,'Skin',0);
% ES5=struct('Nummer',5,'anzElemente',3,'Skin',0);
% ES6=struct('Nummer',6,'anzElemente',3,'Skin',0);
% ES7=struct('Nummer',7,'anzElemente',3,'Skin',0);
% ES8=struct('Nummer',8,'anzElemente',3,'Skin',0);
% ES9=struct('Nummer',9,'anzElemente',3,'Skin',0);
% ES10=struct('Nummer',10,'anzElemente',4,'Skin',0);
% ES11=struct('Nummer',11,'anzElemente',4,'Skin',0);
% ES12=struct('Nummer',12,'anzElemente',4,'Skin',0);
% ES13=struct('Nummer',13,'anzElemente',4,'Skin',0);
% ES14=struct('Nummer',14,'anzElemente',3,'Skin',1);
% ES15=struct('Nummer',15,'anzElemente',3,'Skin',1);
% ES16=struct('Nummer',16,'anzElemente',3,'Skin',1);
% ES17=struct('Nummer',17,'anzElemente',3,'Skin',1);
% ES18=struct('Nummer',18,'anzElemente',4,'Skin',1);
% ES19=struct('Nummer',19,'anzElemente',4,'Skin',1);
% ES20=struct('Nummer',20,'anzElemente',4,'Skin',1);
% ES21=struct('Nummer',21,'anzElemente',4,'Skin',1);
% ESprop=[ES1;ES2;ES3;ES4;ES5;ES6;ES7;ES8;ES9;ES10;ES11;ES12;ES13;ES14;ES15;ES16;ES17;ES18;ES19;ES20;ES21];

% indexMin=1;
% indexMax=length(ES);
% if skin==0
%     while ES(indexMin)<=anzElementeMin
%     indexMin++
% end

end



