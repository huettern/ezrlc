% Reads touchstone file and plots information 
%
% EXAMPLE :
% IN.file = '../sample_files/bsp1.s1p';
% IN.filecomp = '../sample_files/bsp1.s1p.tmp';
% 
addpath 'incomming/s-param_toolbox/sbox'

IN.inFilenmbr=14;
IN.file = ['../sample_files/bsp' int2str(IN.inFilenmbr) '.s1p'];
IN.filecomp = ['../sample_files/bsp' int2str(IN.inFilenmbr) '.s1p.tmp'];

% read Datafile
fid_log = fopen('SXPParse_log.txt','w');
[freq, data, freq_noise, data_noise, Zo] = SXPParse(IN.file, fid_log);

A = load(IN.filecomp);
A = A(:,1) + A(:,2)*j;


data = s2z(data);
data2=data(:); % Data to compare

% Extract data from one port
parsed = data(:);


% Plotting
subplot(221)
loglog(freq,abs(parsed),'b')
title('magnitude')
grid on

subplot(222)
semilogx(freq,angle(parsed))
title('phase')
grid on

subplot(2,2,[3 4])
plot(freq,abs(A)-abs(parsed),'*-r')
title('deviation between MATLAB and Java')
grid on;
%smithchart(parsed)

MaxDeviation=max(abs(A)-abs(parsed))

% This only works with RF toolbox
% data = read(rfdata.data,'../sample_files/bsp1.s1p');





