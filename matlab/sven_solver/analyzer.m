function [rangliste] = analyzer (w,ys,A,ES)
%ermittelt die Fehlersummen und erstellt eine Rangliste
%w=frequenzenliste
%ys=S-parameter Messwerte
%A=Parametermatrix
%ES=erlaubte Ersatzschaltbilder
fehlersummen=zeros(1,length(ES));
    for k=1:length(ES)
        fehlersummen(k)=fehlersummeSAbs(ES(k),w,ys,A(ES(k),:));
    end
    
    %Rangliste sortieren
    rangliste=[ES',fehlersummen'];
    rangliste=sortrows(rangliste,2);
    rangliste=rangliste(:,1);
end