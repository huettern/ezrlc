function [e] = fehlersummeSAbs(n,w,ys,a)
%n=nummer Ersatzschaltbild
%w=frequenzenliste
%ys=liste der S-Parameter messung
%a=Parameter für bestimmtes Ersatzschaltbild

    k=size(w);  %k=Anzahl Messungen
    k=k(2);     %richtige dimension (size liefert z.B. [1 200] zurück)
    e=0;        %Startwert Fehlersumme 
    
    
    
    sparameter=s(n,w,a);        %s-parameter des n. ersatzschaltbildes 
                                %mit den Aktuellen parametern bestimemn
    sparameterAbs=abs(sparameter);
    for x=1:k
        e=e+((sparameterAbs(x)-abs(ys(x)))^2);
    end
end