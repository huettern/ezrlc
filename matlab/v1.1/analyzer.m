function [rangliste] = analyzer (w,yz,A,ES)
%ermittelt die Fehlersummen und erstellt eine Rangliste
%w=frequenzenliste
%ys=S-parameter Messwerte
%A=Parametermatrix
%ES=erlaubte Ersatzschaltbilder
%Rbez=Bezugswiderstand
fehlersummen=zeros(1,length(ES));
    for k=1:length(ES)
        fehlersumme=fehlersummeZAbs(ES(k),w,yz,A(ES(k),:));
        fehlersummen(k)=fehlersumme;
    end
    
    %Rangliste sortieren
    rangliste=[ES',fehlersummen'];
    rangliste=sortrows(rangliste,2);
    rangliste=rangliste(:,1);
end