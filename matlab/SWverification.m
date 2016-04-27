%% pro2 SW verification MATLAB script

% author: noah huetter

clear; clc;


% Test complex data
rawData = dlmread('../java/pro2/tmp.txt');
complexData = complex(rawData(:, 1), rawData(:, 2));

matlabTest;

max(complexData-scattering)

%% Test double data
rawData = dlmread('../java/pro2/tmp.txt');
doubleData = rawData;

matlabTest;

abs(max(doubleData-freq'))


% adjacent = [0+i, 3.123+i*80.0123, 2.0, 3-i*1];
% 
% fileID = fopen('../java/pro2/tmp.txt', 'w');
% fwrite(fileID,adjacent,'%f');
% fclose(fileID);