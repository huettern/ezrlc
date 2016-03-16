% Reads touchstone file and plots information 
%
% EXAMPLE :
% IN.file = '../sample_files/bsp1.s1p';
% IN.filecomp = '../sample_files/bsp1.s1p.tmp';
% 
clf
addpath 'incomming/s-param_toolbox/sbox'

% IN.inFilenmbr=14;
% IN.file = ['../sample_files/bsp' int2str(IN.inFilenmbr) '.s1p'];
% IN.filecomp = ['../sample_files/bsp' int2str(IN.inFilenmbr) '.s1p.tmp'];

IN.file = ['../sample_files/r100l10uZRI.s1p'];
IN.filecomp = ['../sample_files/r100l10uZRI.s1p.tmp'];

% read Datafile
fid_log = fopen('SXPParse_log.txt','w');
[freq, data, freq_noise, data_noise, Zo] = SXPParse(IN.file, fid_log);

A = load(IN.filecomp);
A = A(:,1) + A(:,2)*j;


data = s2z(data);
data2=data(:); % Data to compare

% Extract data from one port
parsed = data2(:);

% Calculate Error
abs_error=abs(A)-abs(parsed);
angle_error=angle(A)-angle(parsed);
re_error=real(A)-real(parsed);
im_error=imag(A)-imag(parsed);

% Plotting
subplot(321)
loglog(freq,abs(parsed),'b')
title('magnitude')
grid on

subplot(322)
semilogx(freq,angle(parsed))
title('phase')
grid on

subplot(3,2,[3 4])
hold on
plot(freq,abs_error,'LineWidth',2)
plot(freq,angle_error,'LineWidth',2)
plot(freq,re_error,'LineWidth',2)
plot(freq,im_error,'LineWidth',2)
title('deviation between MATLAB and Java')
grid on;
legend('abs error','angle error','re error','im error')
%smithchart(parsed)

figure()
subplot(2,1,1)
hold on
grid on
plot(freq,real(parsed)','LineWidth',1)
%plot(freq,imag(parsed)','LineWidth',1)
plot(freq,real(A)','LineWidth',1)
%plot(freq,imag(A)','LineWidth',1)
legend('Real MATLAB','Real Java')


subplot(2,1,2)
hold on
grid on
%plot(freq,real(parsed)','LineWidth',1)
plot(freq,imag(50*50*parsed)','LineWidth',1)
%plot(freq,real(A)','LineWidth',1)
plot(freq,imag(A)','LineWidth',1)
legend('Imag MATLAB','Imag Java')



MaxDeviation=max(abs(A)-abs(parsed))

% This only works with RF toolbox
% data = read(rfdata.data,'../sample_files/bsp1.s1p');





