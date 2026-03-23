import { IFile, NewFile } from './file.model';

export const sampleWithRequiredData: IFile = {
  id: '4f164679-f77b-4214-90fb-95a6a5828dc5',
};

export const sampleWithPartialData: IFile = {
  id: '79029e97-5e85-48cb-be2f-b051bfbb93b0',
};

export const sampleWithFullData: IFile = {
  id: '310e3547-cb5e-407f-82ba-3f387e2aa029',
};

export const sampleWithNewData: NewFile = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
