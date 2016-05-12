function[fehlersumme,fehlerarray]=fehlersummeZAbs(n,w,yz,a)
%n=nummer Ersatzschaltbild
%w=frequenzenliste
%yz=liste der Z messung
%a=Parameter für bestimmtes Ersatzschaltbild

    k=size(w);  %k=Anzahl Messungen
    k=k(2);     %richtige dimension (size liefert z.B. [1 200] zurück)
    
    
    
    impedanz=z(n,w,a);        %impedanzen des n. ersatzschaltbildes 
                                %mit den Aktuellen parametern bestimemn
    impedanzAbs=abs(impedanz);
    for x=1:k
        fehlerarray(x)=((impedanzAbs(x)-abs(yz(x)))^2);
    end
    fehlersumme=sum(fehlerarray);
end