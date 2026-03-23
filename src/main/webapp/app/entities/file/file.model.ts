export interface IFile {
  id: string;
  url?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  lastModifiedBy?: string | null;
  lastModifiedDate?: string | null;
}

export type NewFile = Omit<IFile, 'id'> & { id: null };
